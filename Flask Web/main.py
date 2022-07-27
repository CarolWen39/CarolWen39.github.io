from flask import Flask
from flask import request, jsonify
import requests


ClientId = ""
ClientSecret = ""
AUTHENTICATION_URL = "https://api.artsy.net/api/tokens/xapp_token"
SEARCH_URL = "https://api.artsy.net/api/search"
ARTISTS_URL = "https://api.artsy.net/api/artists"


app = Flask(__name__)


@app.route('/')
def home():
    return app.send_static_file('index.html')


@app.route('/search/<artist_name>', methods=["GET"])
def search(artist_name):
    ''' search artist ''' 
    TOKEN = get_token()
    if request.method == "GET":
        search = requests.get(SEARCH_URL, params={"q": artist_name, "size": 10}, headers={"X-XAPP-Token": TOKEN, "Content-Type": "application/json; charset=UTF-8"})
        data = search.json()["_embedded"]["results"]
        filter_data = filter_ogtype(data)
        return jsonify(filter_data)


@app.route('/detail/<id>', methods=["POST", "GET"])
def detail(id):
    ''' artist detail '''
    TOKEN = get_token()
    if request.method == "GET":
        if id:
            detail = requests.get(ARTISTS_URL+'/'+id, headers={"X-XAPP-Token": TOKEN, "Content-Type": "application/json; charset=UTF-8"})
            detail_data = get_detail_info(detail.json())
            return jsonify(detail_data)
   

def get_token():
    ''' authentication '''
    # if request.method == 'POST':
    authentication = requests.post(AUTHENTICATION_URL, params={"client_id": ClientId, "client_secret": ClientSecret}, headers={"Content-Type": "application/json; charset=UTF-8"})
    data = authentication.json()
    return data['token']

def filter_ogtype(data):
    res = []
    for d in data:
        if d['og_type'] == 'artist':
            # artist name, picture url, artist id
            res.append((d['title'], d["_links"]["thumbnail"]["href"], d["_links"]["self"]["href"].split('/')[-1]))
    return res

def get_detail_info(data):
    # res = {'name': data['name'],
    #     'birthday': data['birthday'],
    #     'deathday': data['deathday'],
    #     'nationality': data['nationality'],
    #     'biography': data['biography']}
    res = [data['name'], data['birthday'], data['deathday'], data['nationality'],data['biography']]
    return res



if __name__ == '__main__':
    app.run(debug=True)
