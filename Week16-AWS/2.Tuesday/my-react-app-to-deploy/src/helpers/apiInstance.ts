import axios from 'axios';
import { fetchAuthSession } from 'aws-amplify/auth'

const thirdPartyApiInstance = axios.create({
    baseURL: 'http://localhost:5000'
});

const myOwnApiInstance = axios.create({
    baseURL: 'https://p67k05629f.execute-api.us-east-2.amazonaws.com/dev/'
});

myOwnApiInstance.interceptors.request.use(async (config) => {
    try {
      const session = await fetchAuthSession();
      const token = session.tokens?.idToken?.toString();
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      else{
        console.log('No Token!')
      }
    } catch (error) {
      console.error('Error fetching token', error);
    }
    return config;
  }, (error) => {
    return Promise.reject(error);
  });

  export { thirdPartyApiInstance, myOwnApiInstance };