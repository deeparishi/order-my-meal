import React from 'react';
import type { Restaurant } from '../types';

interface RestaurantCardProps {
  restaurant: Restaurant;
  onClick?: (restaurant: Restaurant) => void;
}

const RestaurantCard: React.FC<RestaurantCardProps> = ({ restaurant, onClick }) => {
  const handleClick = () => {
    if (onClick) {
      onClick(restaurant);
    }
  };

  const renderStars = (rating: number) => {
    const stars = [];
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 !== 0;

    for (let i = 0; i < fullStars; i++) {
      stars.push(
        <span key={i} className="text-yellow-400">★</span>
      );
    }

    if (hasHalfStar) {
      stars.push(
        <span key="half" className="text-yellow-400">☆</span>
      );
    }

    const emptyStars = 5 - Math.ceil(rating);
    for (let i = 0; i < emptyStars; i++) {
      stars.push(
        <span key={`empty-${i}`} className="text-gray-300">★</span>
      );
    }

    return stars;
  };

  return (
    <div 
      className="card p-0 overflow-hidden hover:shadow-lg transition-shadow duration-300 cursor-pointer transform hover:scale-[1.02] transition-transform"
      onClick={handleClick}
    >
      {/* Restaurant Image */}
      <div className="relative h-48 w-full">
        <img
          src={restaurant.image}
          alt={restaurant.name}
          className="w-full h-full object-cover"
          onError={(e) => {
            const target = e.target as HTMLImageElement;
            target.src = 'https://images.unsplash.com/photo-1555396273-367ea4eb4db5?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80';
          }}
        />
        <div className="absolute top-3 right-3 bg-white rounded-full px-2 py-1 shadow-md">
          <div className="flex items-center space-x-1">
            <span className="text-yellow-400 text-sm">★</span>
            <span className="text-sm font-semibold text-gray-800">
              {restaurant.rating.toFixed(1)}
            </span>
          </div>
        </div>
      </div>

      {/* Restaurant Info */}
      <div className="p-4">
        <div className="flex items-start justify-between mb-2">
          <h3 className="text-lg font-semibold text-gray-900 truncate">
            {restaurant.name}
          </h3>
        </div>
        
        <p className="text-sm text-gray-600 mb-2 capitalize">
          {restaurant.cuisine} Cuisine
        </p>
        
        {restaurant.description && (
          <p className="text-sm text-gray-500 mb-3 line-clamp-2">
            {restaurant.description}
          </p>
        )}

        <div className="flex items-center justify-between text-sm text-gray-600">
          <div className="flex items-center space-x-1">
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <span>{restaurant.deliveryTime}</span>
          </div>
          
          {restaurant.minimumOrder && (
            <div className="flex items-center space-x-1">
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1" />
              </svg>
              <span>Min ${restaurant.minimumOrder}</span>
            </div>
          )}
        </div>

        {/* Rating Stars */}
        <div className="flex items-center mt-3 pt-3 border-t border-gray-100">
          <div className="flex items-center space-x-1">
            {renderStars(restaurant.rating)}
          </div>
          <span className="ml-2 text-xs text-gray-500">
            ({restaurant.rating}/5.0)
          </span>
        </div>
      </div>
    </div>
  );
};

export default RestaurantCard;