import defaultUser from '../model/user'
import defaultDeck from '../model/deck'

const getDefaultState = () => ({
  user: defaultUser(),
  deck: defaultDeck(),
  editorMode: '',
  searchResults: {data: ""},
  viewSearch: false,
})

export { getDefaultState }