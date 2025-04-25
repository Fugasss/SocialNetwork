import type { Metadata } from "next";
import { Geist_Mono } from "next/font/google";
import "./globals.css";
import ReduxProvider from "@/store/ReduxProvider";
import ThemesProvider from "./_components/layout/ThemesProvider/ThemesProvider";
import AuthWrapper from "./_components/layout/AuthWrapper/AuthWrapper";
import Header from "./_components/layout/Header/Header";

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Social Network",
  description: "A social network for everyone",
  icons: {
    icon: "/favicon.ico"
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body className={`${geistMono.className} antialiased`}>
        <ReduxProvider>
          <ThemesProvider>
            <AuthWrapper>
              <header>
                <Header/>
              </header>
              <main>
                {children}
              </main>
              <footer></footer>
            </AuthWrapper>
          </ThemesProvider>
        </ReduxProvider>
      </body>
    </html>
  );
}
