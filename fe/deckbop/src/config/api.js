const baseUrl = baseLocalURL;
const baseLocalURL = 'http://localhost:8081';

const userLogin = '/user/login';
const userLoginUrl = baseLocalURL + userLogin;
const userRegistration = '/user/register';
const userRegistrationUrl = baseLocalURL + userRegistration;

const activate = "/user/activate"
const activateUrl = baseLocalURL + activate;

const jsonContentHeader = {"Content-Type":"application/json"}

export {
  baseUrl,
  userLoginUrl,
  userRegistrationUrl,
  activateUrl,
  jsonContentHeader,
};