import axios from 'axios'

import { baseUrl } from '../config/api'

export default axios.create({
  baseUrl: baseUrl  
})
