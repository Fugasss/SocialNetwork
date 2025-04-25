import React from 'react';
import { TextInputProps } from './TextInput';

type TextAreaProps = TextInputProps & {
    
    rows?: number;
};

export default function TextArea({
    value,
    placeholder,
    readonly,
    handleChange,
    rows,
}: TextAreaProps) {
    return (
        <textarea
            rows={rows ?? 4}
            readOnly={readonly}
            className="px-3 py-2 border-2 w-full bg-transparent
                      border-black"
            value={value}
            placeholder={placeholder}
            onChange={(e) => handleChange(e.target.value)}
        />
    );
};