const scryfallBaseUrl = 'https://api.scryfall.com/'
const search = 'cards/search'
const collection = 'cards/collection'
const scryfallSearchUrl = scryfallBaseUrl + search
const scryfallCollectionUrl = scryfallBaseUrl + collection

const getSearchUrl = (text) => `${scryfallSearchUrl}/?q=${text}`

export {
  scryfallBaseUrl,
  scryfallSearchUrl,
  scryfallCollectionUrl,
  getSearchUrl,
}