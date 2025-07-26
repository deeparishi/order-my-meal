import type { Restaurant } from '../types';
import { apiService } from './api';

class RestaurantService {
  private readonly RESTAURANT_ENDPOINTS = {
    LIST: '/restaurants',
    DETAILS: '/restaurants',
    SEARCH: '/restaurants/search'
  };

  async getRestaurants(): Promise<Restaurant[]> {
    try {
      const response = await apiService.get<Restaurant[]>(
        this.RESTAURANT_ENDPOINTS.LIST
      );
      return response.data || [];
    } catch (error) {
      console.error('Failed to fetch restaurants:', error);
      // Return mock data as fallback
      return this.getMockRestaurants();
    }
  }

  async getRestaurantById(id: string): Promise<Restaurant | null> {
    try {
      const response = await apiService.get<Restaurant>(
        `${this.RESTAURANT_ENDPOINTS.DETAILS}/${id}`
      );
      return response.data || null;
    } catch (error) {
      console.error('Failed to fetch restaurant details:', error);
      return null;
    }
  }

  async searchRestaurants(query: string): Promise<Restaurant[]> {
    try {
      const response = await apiService.get<Restaurant[]>(
        this.RESTAURANT_ENDPOINTS.SEARCH,
        { q: query }
      );
      return response.data || [];
    } catch (error) {
      console.error('Failed to search restaurants:', error);
      return [];
    }
  }

  // Mock data for development/fallback
  private getMockRestaurants(): Restaurant[] {
    return [
      {
        id: '1',
        name: 'Pizza Palace',
        cuisine: 'Italian',
        image: 'https://images.unsplash.com/photo-1513104890138-7c749659a591?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
        rating: 4.5,
        description: 'Authentic Italian pizzas with fresh ingredients',
        deliveryTime: '25-35 min',
        minimumOrder: 15
      },
      {
        id: '2',
        name: 'Burger Barn',
        cuisine: 'American',
        image: 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
        rating: 4.2,
        description: 'Juicy burgers and crispy fries',
        deliveryTime: '20-30 min',
        minimumOrder: 12
      },
      {
        id: '3',
        name: 'Sushi Zen',
        cuisine: 'Japanese',
        image: 'https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
        rating: 4.8,
        description: 'Fresh sushi and traditional Japanese dishes',
        deliveryTime: '30-40 min',
        minimumOrder: 20
      },
      {
        id: '4',
        name: 'Taco Fiesta',
        cuisine: 'Mexican',
        image: 'https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
        rating: 4.3,
        description: 'Authentic Mexican tacos and burritos',
        deliveryTime: '15-25 min',
        minimumOrder: 10
      },
      {
        id: '5',
        name: 'Curry House',
        cuisine: 'Indian',
        image: 'https://images.unsplash.com/photo-1565557623262-b51c2513a641?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
        rating: 4.6,
        description: 'Spicy curries and traditional Indian cuisine',
        deliveryTime: '25-35 min',
        minimumOrder: 18
      },
      {
        id: '6',
        name: 'Pasta Corner',
        cuisine: 'Italian',
        image: 'https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
        rating: 4.4,
        description: 'Fresh pasta and Italian specialties',
        deliveryTime: '20-30 min',
        minimumOrder: 14
      }
    ];
  }
}

export const restaurantService = new RestaurantService();