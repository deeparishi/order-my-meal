import { toast, type ToastContentProps, type ToastOptions } from 'react-toastify';

const defaultOptions: ToastOptions = {
  position: 'top-right',
  autoClose: 4000,
  hideProgressBar: false,
  closeOnClick: true,
  pauseOnHover: true,
  draggable: true,
};

export const showToast = {
  success: (message: string, options?: Partial<ToastOptions>) => {
    toast.success(message, { ...defaultOptions, ...options });
  },

  error: (message: string, options?: Partial<ToastOptions>) => {
    toast.error(message, { ...defaultOptions, ...options });
  },

  info: (message: string, options?: Partial<ToastOptions>) => {
    toast.info(message, { ...defaultOptions, ...options });
  },

  warning: (message: string, options?: Partial<ToastOptions>) => {
    toast.warning(message, { ...defaultOptions, ...options });
  },

  promise: <T>(
    promise: Promise<T>,
    messages: {
      pending: string;
      success: string | ((data: T) => string);
      error: string | ((error: any) => string);
    },
    options?: Partial<ToastOptions>
  ) => {
    const toastMessages = {
      pending: messages.pending,
      success:
        typeof messages.success === 'function'
          ? {
            render: (props: ToastContentProps<T>) => {
              // Handle the case where props.data might be undefined
              if (props.data !== undefined) {
                return (messages.success as (data: T) => string)(props.data);
              }
              return 'Success'; // Fallback message
            }
          }
          : messages.success,
      error:
        typeof messages.error === 'function'
          ? {
            render: (props: ToastContentProps<any>) => {
              // Handle the case where props.data might be undefined
              if (props.data !== undefined) {
                return (messages.error as (err: any) => string)(props.data);
              }
              return 'An error occurred'; // Fallback message
            }
          }
          : messages.error,
    };

    return toast.promise(promise, toastMessages, { ...defaultOptions, ...options });
  }
};

// Utility function to show API error messages
export const showApiError = (error: any) => {
  const message = error?.message || 'An unexpected error occurred';
  showToast.error(message);
};

// Utility function to show success messages for common actions
export const showSuccessMessage = (action: string) => {
  const messages: Record<string, string> = {
    login: 'Welcome back! Login successful.',
    register: 'Account created successfully! Welcome aboard.',
    logout: 'Logged out successfully. See you soon!',
    update: 'Information updated successfully.',
    delete: 'Item deleted successfully.',
    create: 'Item created successfully.',
  };

  const message = messages[action] || `${action} completed successfully.`;
  showToast.success(message);
};