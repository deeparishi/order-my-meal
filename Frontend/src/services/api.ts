import type { AxiosError, AxiosInstance, AxiosResponse } from "axios";
import axios from "axios";
import type { ApiError, ApiResponse } from "../types";

class ApiService {
  private api: AxiosInstance;
  private readonly baseURL: string;

  constructor() {
    this.baseURL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
    
    this.api = axios.create({
      baseURL: this.baseURL,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json',
      },
    });

    this.setupInterceptors();
  }

  private setupInterceptors(): void {
    // Request interceptor for adding auth token
    this.api.interceptors.request.use(
      (config) => {
        const token = this.getToken();
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );

    // Response interceptor for handling common responses and errors
    this.api.interceptors.response.use(
      (response: AxiosResponse) => {
        return response;
      },
      async (error: AxiosError) => {
        const originalRequest = error.config;
        console.log('API Error:', error, originalRequest);
        // Handle 401 errors (unauthorized)
        if (error.response?.status === 401) {
          this.removeToken();
          window.location.href = '/login';
          return Promise.reject(error);
        }

        // Handle network errors
        if (!error.response) {
          const networkError: ApiError = {
            message: 'Network error. Please check your internet connection.',
            status: 0,
            code: 'NETWORK_ERROR'
          };
          return Promise.reject(networkError);
        }

        // Extract error message from response
        const apiError: ApiError = {
          message: this.extractErrorMessage(error),
          status: error.response.status,
          code: error.code
        };

        return Promise.reject(apiError);
      }
    );
  }

  private extractErrorMessage(error: AxiosError): string {
    if (error.response?.data) {
      const data = error.response.data as any;
      
      // Try different possible error message fields
      if (data.message) return data.message;
      if (data.error) return data.error;
      if (data.errors && Array.isArray(data.errors) && data.errors.length > 0) {
        return data.errors[0].message || data.errors[0];
      }
    }

    // Fallback error messages based on status code
    switch (error.response?.status) {
      case 400:
        return 'Bad request. Please check your input.';
      case 401:
        return 'Authentication required. Please login again.';
      case 403:
        return 'Access denied. You do not have permission to perform this action.';
      case 404:
        return 'The requested resource was not found.';
      case 500:
        return 'Internal server error. Please try again later.';
      case 503:
        return 'Service temporarily unavailable. Please try again later.';
      default:
        return error.message || 'An unexpected error occurred.';
    }
  }

  // Token management
  private getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  public setToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  public removeToken(): void {
    localStorage.removeItem('authToken');
  }

  public isAuthenticated(): boolean {
    return !!this.getToken();
  }

  // Generic API methods
  public async get<T>(url: string, params?: any): Promise<ApiResponse<T>> {
    try {
      const response = await this.api.get(url, { params });
      return this.handleResponse<T>(response);
    } catch (error) {
      throw this.handleError(error);
    }
  }

  public async post<T>(url: string, data?: any): Promise<ApiResponse<T>> {
    try {
      const response = await this.api.post(url, data);
      return this.handleResponse<T>(response);
    } catch (error) {
      throw this.handleError(error);
    }
  }

  public async put<T>(url: string, data?: any): Promise<ApiResponse<T>> {
    try {
      const response = await this.api.put(url, data);
      return this.handleResponse<T>(response);
    } catch (error) {
      throw this.handleError(error);
    }
  }

  public async delete<T>(url: string): Promise<ApiResponse<T>> {
    try {
      const response = await this.api.delete(url);
      return this.handleResponse<T>(response);
    } catch (error) {
      throw this.handleError(error);
    }
  }

  private handleResponse<T>(response: AxiosResponse): ApiResponse<T> {
    return {
      success: true,
      data: response.data,
      message: 'Request successful'
    };
  }

  private handleError(error: any): ApiError {
    if (error.message && error.status !== undefined) {
      // This is already an ApiError from our interceptor
      return error;
    }
    
    // Fallback for any other errors
    return {
      message: 'An unexpected error occurred',
      status: 500
    };
  }

  // Retry mechanism for failed requests
  public async retryRequest<T>(
    requestFn: () => Promise<ApiResponse<T>>, 
    maxRetries: number = 3,
    delay: number = 1000
  ): Promise<ApiResponse<T>> {
    let lastError: ApiError;
    
    for (let i = 0; i < maxRetries; i++) {
      try {
        return await requestFn();
      } catch (error) {
        lastError = error as ApiError;
        
        // Don't retry for client errors (4xx)
        if (lastError.status && lastError.status >= 400 && lastError.status < 500) {
          throw lastError;
        }
        
        // Wait before retrying
        if (i < maxRetries - 1) {
          await new Promise(resolve => setTimeout(resolve, delay * Math.pow(2, i)));
        }
      }
    }
    
    throw lastError!;
  }
}

// Export singleton instance
export const apiService = new ApiService();