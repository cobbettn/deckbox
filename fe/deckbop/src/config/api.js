const baseLocalURL = 'http://localhost:8081';

const baseUrl = baseLocalURL;

const user = '/user'
const userUrl = baseUrl + user
const userLogin = `${user}/login`;
const userLoginUrl = baseUrl + userLogin;
const userRegistration = `${user}/register`;
const userRegistrationUrl = baseUrl + userRegistration;
const activate = `${user}/activate`;
const activateUrl = baseUrl + activate;

const deck = '/deck'
const deckUrl = baseUrl + deck

const jsonContentHeader = {"Content-Type": "application/json"}
const authTokenFactory = token => ({"Authorization": `Bearer ${token}`})

export {
  baseUrl,
  userUrl,
  userLoginUrl,
  userRegistrationUrl,
  activateUrl,
  deckUrl,
  jsonContentHeader,
  authTokenFactory
};