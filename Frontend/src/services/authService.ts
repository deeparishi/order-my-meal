import type { AuthResponse, LoginRequest, RegisterRequest, User } from '../types';
import { apiService } from './api';

class AuthService {

  private readonly BASE_URL = '/gateway/idp';

  private readonly AUTH_ENDPOINTS = {
    LOGIN: '/v1/auth/login',
    REGISTER: '/auth/register',
    LOGOUT: '/auth/logout',
    PROFILE: '/auth/profile'
  };

  async login(credentials: LoginRequest): Promise<AuthResponse> {
    try {
      const response = await apiService.post<AuthResponse>(
        this.BASE_URL + this.AUTH_ENDPOINTS.LOGIN,
        credentials
      );

      if (response.data?.data?.token) {
              console.log('Login response:', response);
        apiService.setToken(response.data.data.token);
        this.setUserData(response.data.data.userResponse);
      }

      return response.data!;
    } catch (error) {
      throw error;
    }
  }

  async register(userData: RegisterRequest): Promise<AuthResponse> {
    try {
      // Remove confirmPassword before sending to API
      const { confirmPassword, ...requestData } = userData;
      
      const response = await apiService.post<AuthResponse>(
        this.AUTH_ENDPOINTS.REGISTER,
        requestData
      );

      if (response.data?.data?.token) {
        apiService.setToken(response.data.data.token);
        this.setUserData(response.data.data.userResponse);
      }

      return response.data!;
    } catch (error) {
      throw error;
    }
  }

  async logout(): Promise<void> {
    try {
      // Call logout endpoint if available
      await apiService.post(this.AUTH_ENDPOINTS.LOGOUT);
    } catch (error) {
      // Log error but don't throw - we still want to clear local data
      console.error('Logout API call failed:', error);
    } finally {
      this.clearAuthData();
    }
  }

  async getCurrentUser(): Promise<User | null> {
    try {
      if (!apiService.isAuthenticated()) {
        return null;
      }

      const response = await apiService.get<User>(this.AUTH_ENDPOINTS.PROFILE);
      return response.data!;
    } catch (error) {
      console.error('Failed to get current user:', error);
      this.clearAuthData();
      return null;
    }
  }

  isAuthenticated(): boolean {
    return apiService.isAuthenticated() && !!this.getUserData();
  }

  getUserData(): User | null {
    try {
      const userData = localStorage.getItem('userData');
      return userData ? JSON.parse(userData) : null;
    } catch (error) {
      console.error('Failed to parse user data:', error);
      return null;
    }
  }

  private setUserData(user: User): void {
    console.log('Setting user data:', user)
    localStorage.setItem('userData', JSON.stringify(user));
    console.log('User data saved to localStorage');
  }

  private clearAuthData(): void {
    apiService.removeToken();
    localStorage.removeItem('userData');
  }

  // Utility method to check if user has specific role
  hasRole(role: string): boolean {
    const user = this.getUserData();
    return user?.role === role;
  }

  // Method to refresh token if needed
  async refreshToken(): Promise<boolean> {
    try {
      // This would typically call a refresh token endpoint
      // For now, we'll check if current token is still valid
      const user = await this.getCurrentUser();
      return !!user;
    } catch (error) {
      console.error('Token refresh failed:', error);
      this.clearAuthData();
      return false;
    }
  }
}

export const authService = new AuthService();