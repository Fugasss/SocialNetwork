import { useTheme } from "next-themes";
import { useEffect, useState } from "react";

export default function Loading() {
    const { theme } = useTheme();
    const [mounted, setMounted] = useState(false);
  
    useEffect(() => {
      setMounted(true);
    }, []);
  
    if (!mounted) return null;
  
    return (
      <div className="flex justify-center items-center animate-spin">
        <div
          className={`bg-transparent border-t-4 border-r-2 border-r-transparent p-4 rounded-full ${theme === 'dark' ? 'border-white' : 'border-black'}`}
        ></div>
      </div>
    );
  }