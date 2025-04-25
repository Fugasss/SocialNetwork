'use client';

import Button from '@/app/_components/common/buttons/Button';
import Loading from '@/app/_components/common/Loading';
import TextInput from '@/app/_components/common/inputs/TextInput';
import { EApi, ERoutes } from '@/enums';
import { setCookie } from '@/utils/cookie';
import fetchApi from '@/utils/fetchApi';
import { useLogin } from '@/utils/hooks/useLogin';
import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Form from '@/app/_components/common/Form';

export default function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const router = useRouter();
  const { setLogined } = useLogin();

  const sendForm = async (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();

    try {
      setIsLoading(true);

      setError('');

      if (email.length < 6 || password.length < 6) {
        setError(`Error occured: Invalid email or password`);

        setIsLoading(false);

        return;
      }

      const response = await fetchApi(EApi.LOGIN, 'POST', { email, password });
      const data = await response!.json();
      setCookie('token', data.accessToken);
      setCookie('refreshToken', data.refreshToken);
      setLogined(true);

      router.push(ERoutes.HOME);
    } catch (err) {
      if (typeof err === 'object' && 'message' in err! && 'stack' in err!) {
        if (err.message == '409') {
          setError("Conflict: User with this email already exists");
        } else {
          setError(`Error occured: ${err.message}`);
        }
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Form>
      <h1 className="text-3xl">Login</h1>

      <TextInput
        placeholder="Email"
        value={email}
        handleChange={setEmail}
      />

      <TextInput
        isPassword
        placeholder="Password"
        value={password}
        handleChange={setPassword}
      />

      <div className="w-28">
        {isLoading ? (
          <Loading />
        ) : (
          <Button text="Login" handleClick={sendForm} />
        )}
      </div>

      <div className="flex gap-1">
        <p>Don&apos;t have an account?</p>
        <Link href={ERoutes.REGISTRATION}>
          <p className="hover:underline text-link">register</p>
        </Link>
      </div>

      <p className="text-warn">{error}</p>
    </Form>
  );
}