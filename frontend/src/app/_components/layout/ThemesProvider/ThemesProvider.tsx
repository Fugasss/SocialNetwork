import { ThemeProvider } from 'next-themes';
import React, { ReactNode } from 'react';

export default function ThemesProvider({ children }: { children: ReactNode }) {
  return (
    <ThemeProvider attribute={'class'} defaultTheme="light">
      {children}
    </ThemeProvider>
  );
}