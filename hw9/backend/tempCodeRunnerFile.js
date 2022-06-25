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
                        artistList.push({"name": artistName, "img": artistImg, "id": artistId});
                    }
                }
                res.send(artistList);
            });
        });