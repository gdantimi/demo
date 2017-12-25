import {environmentPort} from "./env.js";

export const environment = {
  production: true,
  apiUrl: 'http://localhost:' + environmentPort
};
