import React, { useState, useEffect } from 'react';
import { restaurantService } from '../services/restaurantService';
import RestaurantCard from '../components/RestaurantCard';
import Loading from '../components/Loading';
import { showToast, showApiError } from '../utils/toast';
import type { Restaurant } from '../types';

const Restaurants: React.FC = () => {
  const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredRestaurants, setFilteredRestaurants] = useState<Restaurant[]>([]);

  useEffect(() => {
    loadRestaurants();
  }, []);

  useEffect(() => {
    // Filter restaurants based on search term
    if (searchTerm.trim() === '') {
      setFilteredRestaurants(restaurants);
    } else {
      const filtered = restaurants.filter(restaurant =>
        restaurant.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        restaurant.cuisine.toLowerCase().includes(searchTerm.toLowerCase())
      );
      setFilteredRestaurants(filtered);
    }
  }, [searchTerm, restaurants]);

  const loadRestaurants = async () => {
    try {
      setIsLoading(true);
      const data = await restaurantService.getRestaurants();
      setRestaurants(data);
      setFilteredRestaurants(data);
    } catch (error) {
      showApiError(error);
      showToast.error('Failed to load restaurants. Please try again.');
    } finally {
      setIsLoading(false);
    }
  };

  const handleRestaurantClick = (restaurant: Restaurant) => {
    showToast.info(`${restaurant.name} selected! Menu coming soon...`);
    // TODO: Navigate to restaurant menu page
    console.log('Restaurant selected:', restaurant);
  };

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  if (isLoading) {
    return <Loading fullScreen text="Loading restaurants..." />;
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">
            Restaurants
          </h1>
          <p className="text-gray-600">
            Discover and order from your favorite local restaurants
          </p>
        </div>

        {/* Search Bar */}
        <div className="mb-8">
          <div className="max-w-md">
            <div className="relative">
              <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg
                  className="h-5 w-5 text-gray-400"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                  />
                </svg>
              </div>
              <input
                type="text"
                className="input-field pl-10"
                placeholder="Search restaurants or cuisines..."
                value={searchTerm}
                onChange={handleSearchChange}
              />
            </div>
          </div>
        </div>

        {/* Restaurant Count */}
        <div className="mb-6">
          <p className="text-sm text-gray-600">
            {filteredRestaurants.length} restaurant{filteredRestaurants.length !== 1 ? 's' : ''} found
            {searchTerm && ` for "${searchTerm}"`}
          </p>
        </div>

        {/* No Results */}
        {filteredRestaurants.length === 0 && !isLoading && (
          <div className="text-center py-12">
            <div className="w-24 h-24 mx-auto mb-4 bg-gray-200 rounded-full flex items-center justify-center">
              <svg
                className="w-12 h-12 text-gray-400"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                />
              </svg>
            </div>
            <h3 className="text-lg font-medium text-gray-900 mb-2">
              No restaurants found
            </h3>
            <p className="text-gray-500 mb-4">
              {searchTerm 
                ? `We couldn't find any restaurants matching "${searchTerm}"`
                : "No restaurants are currently available"
              }
            </p>
            {searchTerm && (
              <button
                onClick={() => setSearchTerm('')}
                className="btn-primary"
              >
                Clear search
              </button>
            )}
          </div>
        )}

        {/* Restaurant Grid */}
        {filteredRestaurants.length > 0 && (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {filteredRestaurants.map((restaurant) => (
              <RestaurantCard
                key={restaurant.id}
                restaurant={restaurant}
                onClick={handleRestaurantClick}
              />
            ))}
          </div>
        )}

        {/* Load More (for future pagination) */}
        {filteredRestaurants.length > 0 && (
          <div className="mt-12 text-center">
            <button
              className="btn-secondary"
              onClick={() => showToast.info('Load more functionality coming soon!')}
            >
              Load More Restaurants
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Restaurants;