import React from 'react';

export type TextInputProps = {
    handleChange: (value: string) => void;
    value: string;
    placeholder: string;
    isPassword?: boolean;
    readonly?: boolean;
};

export default function TextInput({
    value,
    placeholder,
    isPassword,
    readonly,
    handleChange,
}: TextInputProps) {
    return (
        <input
            type={isPassword ? 'password' : 'text'}
            readOnly={readonly}
            className="px-3 py-2 border-2 w-full bg-transparent
                      border-black"
            value={value}
            placeholder={placeholder}
            onChange={(e) => handleChange(e.target.value)}
        />
    );
};