import { EApi } from '@/enums';
import { getCookie, setCookie } from './cookie';

type RequestMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';

const fetchApi = async (path: string, method: RequestMethod, body = {}) => {
  async function sendRequest() {
    let response: Response;
    const accessToken = getCookie('token');

    if (method === 'GET') {
      response = await fetch(path, {
        method,
        headers: {
          authorization: `Bearer ${accessToken}`,
        },
      });
    } else {
      response = await fetch(path, {
        method,
        headers: {
          authorization: `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
      });
    }

    return response;
  }

  let response = await sendRequest();

  console.log(path);

  if (!response.ok) {
    if (response.status == 401) {

      await tryRefreshTokens();
      response = await sendRequest();

      if (!response.ok) {
        throw new Error(`${response.status}`);
      }

    }
  }

  if (response.status == 204) return;

  return response;
};

const tryRefreshTokens = async () => {
  const refreshToken = getCookie('refreshToken');
  const response = await fetch(EApi.REFRESH, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ "refreshToken": refreshToken, })
  });

  console.log("Refreshing tokens", response.status);

  if (!response.ok) {
    console.log('Error while refreshing tokens', response.status);
    return false;
  }

  const newTokens = await response.json();

  setCookie('token', newTokens.accessToken);
  setCookie('refreshToken', newTokens.refreshToken);
}

export default fetchApi;