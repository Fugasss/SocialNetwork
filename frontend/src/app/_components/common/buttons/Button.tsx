import React from 'react';

type ButtonProps = {
  text: string;
  handleClick: (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
  disabled?: boolean;
};

export default function Button({ text, handleClick, disabled }: ButtonProps) {
  disabled = disabled ?? false;

  return (
    <button
      disabled={disabled}
      className="w-full py-2 border-1 transition 
                        px-1 md:px-3
                        text-center border-black
                        hover:not-disabled:bg-black hover:not-disabled:text-white
                        disabled:bg-gray-300 disabled:text-gray-500"
      onClick={handleClick}
    >
      {text}
    </button>
  );
}