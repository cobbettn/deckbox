const baseUrl = baseLocalURL;
const baseLocalURL = 'http://localhost:8081';
const userLogin = '/user/login';
const userLoginUrl = baseLocalURL + userLogin;
const userRegistration = '/user/register';
const userRegistrationUrl = baseLocalURL + userRegistration;

export {
  baseUrl,
  userLoginUrl,
  userRegistrationUrl,
};