import Image from 'next/image';
import React from 'react';

type DeleteButtonProps = {
  icon: string;
  handleClick: (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
  disabled?: boolean;
  width?: number;
  height?: number;
};

export default function DeleteButton({ icon, handleClick, disabled, width, height }: DeleteButtonProps) {
  disabled = disabled || false;

  return (
    <button
      disabled={disabled}
      className="w-fit h-fit
                p-0.5 border-1 transition
                border-black
                bg-red-500 
                hover:bg-red-600"
      onClick={handleClick}
    >
      <Image src={icon} alt='delete icon' width={width || 32} height={height || 32}/>
    </button>
  );
}