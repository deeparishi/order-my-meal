

export interface LoginRequest {
  emailOrMobileNumber: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  confirmPassword: string;
  name?: string;
}

// Restaurant Types
export interface Restaurant {
  id: string;
  name: string;
  cuisine: string;
  image: string;
  rating: number;
  description?: string;
  deliveryTime?: string;
  minimumOrder?: number;
}

// API Response Types
export interface ApiResponse<T = any> {
  success: boolean;
  data?: T;
  datum?: T;
  error?: string;
  message?: string;
}

export interface AuthResponse {
  success: boolean;
  statusCode: number;
  status: string;
  data: AuthData;
  datum?: any;
  timeStamp: string;
}

export interface AuthData {
  userResponse: User;
  token: string;
  refreshToken: string;
}

export interface User {
  username: string;
  password: string;
  address: Address[];
  role: 'STANDARD' | 'ADMIN';
  mobileNumber: string;
  emailId: string;
}

export interface Address {
  number: string;
  street: string;
  landMark: string;
  city: string;
  zipcode: number;
  district: string;
}


export interface ApiError {
  message: string;
  status?: number;
  code?: string;
}

export interface FormErrors {
  [key: string]: string;
}

export type ToastType = 'success' | 'error' | 'info' | 'warning';