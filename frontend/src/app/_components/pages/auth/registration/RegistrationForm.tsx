'use client';

import Button from '@/app/_components/common/buttons/Button';
import Loading from '@/app/_components/common/Loading';
import TextInput from '@/app/_components/common/inputs/TextInput';
import { EApi, ERoutes } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import Form from '@/app/_components/common/Form';

export default function RegistrationForm() {
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [retryPassword, setRetryPassword] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const router = useRouter();

  const sendForm = async (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();

    try {
      setIsLoading(true);

      setError('');
        
      if (email.length < 6 || password.length < 6 || password !== retryPassword) {
        setError(`Error occured: Invalid email or password`);

        setIsLoading(false);

        return;
      }

      await fetchApi(EApi.REGISTER, 'POST', {username, email, password });

      router.push(ERoutes.LOGIN);
    } catch (err) {
      if (typeof err === 'object' && 'message' in err! && 'stack' in err!) {
        if (err.message == '404') {
          setError("User doesn't exists");
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
      <h1 className="text-3xl">Registration</h1>

      <TextInput
        placeholder="Username"
        value={username}
        handleChange={setUsername}
      />

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

    <TextInput
        isPassword
        placeholder="Retry Password"
        value={retryPassword}
        handleChange={setRetryPassword}
      />

      <div className="w-28">
        {isLoading ? (
          <Loading />
        ) : (
          <Button text="Register" handleClick={sendForm} />
        )}
      </div>

      <div className="flex gap-1">
        <p>Already have an account?</p>
        <Link href={ERoutes.LOGIN}>
          <p className="hover:underline text-link">login</p>
        </Link>
      </div>

      <p className="text-warn">{error}</p>
    </Form>
  );
}