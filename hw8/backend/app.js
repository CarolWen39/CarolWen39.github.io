const express = require('express');
const axios = require('axios').default;
const bodyParser = require('body-parser');
const cors = require('cors');

const ClientId = "f306e380923c83710750";
const ClientSecret = "3ef69bf57eece01c21604cd283bc04f6";
const AUTHENTICATION_URL = "https://api.artsy.net/api/tokens/xapp_token";
const SEARCH_URL = "https://api.artsy.net/api/search";
const ARTISTS_URL = "https://api.artsy.net/api/artists";
const ARTWORK_URL = "https://api.artsy.net/api/artworks";
const GENES_URL = "https://api.artsy.net/api/genes";


const app = express();
app.use(express.static(__dirname +"/frontend"));
app.use(cors({ origin: "*" }));
app.use(bodyParser.urlencoded({ extended: true }));

const port = parseInt(process.env.PORT) || 8080;
app.listen(port);

function getToken() {
    return axios.post(AUTHENTICATION_URL, {
        "client_id": ClientId,
        "client_secret": ClientSecret
    },
        headers = {
            "Content-Type": "application/json; charset=UTF-8"
        })
        .then(function (res) {
            return res.data.token;
        });
}


app.get('/search', function (req, res) {
    getToken()
        .then(function (result) {
            const TOKEN = result;
            const artistName = req.query.artistName;
            var artistList = [];
            // search
            const config = {
                method: 'get',
                url: SEARCH_URL,
                params: {
                    "q": artistName,
                    "size": 10
                },
                headers: {
                    "X-XAPP-Token": TOKEN,
                    "Content-Type": "application/json; charset=UTF-8"
                }
            };
            axios(config).then(function (resp) {
                const artistReturnData = resp.data._embedded.results;
                for (var i = 0; i < artistReturnData.length; i++) {
                    if (artistReturnData[i].og_type == 'artist') {
                        const artistName = artistReturnData[i].title;
                        const artistImg = artistReturnData[i]._links.thumbnail.href;
                        const hrefList = artistReturnData[i]._links.self.href.split('/');
                        const artistId = hrefList[hrefList.length - 1];
                        artistList.push([artistName, artistImg, artistId]);
                    }
                }
                res.send(artistList);
            });
        });
});


app.get('/detail', function (req, res) {
    getToken()
        .then(function (result) {
            const TOKEN = result;
            const artistId = req.query.artistId;
            if (artistId) {
                // detail
                const config = {
                    method: 'get',
                    url: ARTISTS_URL + '/' + artistId,
                    headers: {
                        "X-XAPP-Token": TOKEN,
                        "Content-Type": "application/json; charset=UTF-8"
                    }
                }
                var detailList;
                axios(config).then(function (resp) {
                    detailList = [resp.data.name, resp.data.birthday, resp.data.deathday, resp.data.nationality, resp.data.biography];
                    res.send(detailList);
                });
            }
        });
});


app.get('/artwork', function (req, res) {
    getToken()
        .then(function (result) {
            const TOKEN = result;
            const artistId = req.query.artistId;
            var artworkList = [];
            if (artistId) {
                // artwork
                const config = {
                    method: 'get',
                    url: ARTWORK_URL,
                    params: {
                        "artist_id": artistId,
                        "size": 10
                    },
                    headers: {
                        "X-XAPP-Token": TOKEN,
                        "Content-Type": "application/json; charset=UTF-8"
                    }
                };
                
                axios(config).then(function (resp) {
                    const artworkReturnData = resp.data._embedded.artworks;
                    for (var i = 0; i < artworkReturnData.length; i++) {
                        const artworkId = artworkReturnData[i].id;
                        const artworkTitle = artworkReturnData[i].title;
                        const artworkDate = artworkReturnData[i].date;
                        const artworkImg = artworkReturnData[i]._links.thumbnail.href;
                        artworkList.push([artworkId, artworkTitle, artworkDate, artworkImg]);
                    }
                    res.send(artworkList);
                });
                
            }
        });
});


app.get('/categories', function (req, res) {
    getToken()
        .then(function (result) {
            const TOKEN = result;
            const artworkId = req.query.artworkId;
            var categorieList = [];
            if (artworkId) {
                // categories
                const config = {
                    method: 'get',
                    url: GENES_URL,
                    params: {
                        "artwork_id": artworkId
                    },
                    headers: {
                        "X-XAPP-Token": TOKEN,
                        "Content-Type": "application/json; charset=UTF-8"
                    }
                };
                axios(config).then(function (resp) {
                    const categoryReturnData = resp.data._embedded.genes;
                    for (var i = 0; i < categoryReturnData.length; i++) {
                        const categoryName = categoryReturnData[i].name;
                        const categoryImg = categoryReturnData[i]._links.thumbnail.href;
                        categorieList.push([categoryName, categoryImg]);
                    }
                    res.send(categorieList);
                });
                
            }
        });
});

