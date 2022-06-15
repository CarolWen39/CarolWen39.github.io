import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NodebackendService {

  constructor(private http: HttpClient) { }
  // rootURL = '/';
  // serverUrl = 'http://localhost:8080';

  searchArtists(artist: string) {
    let headers = new HttpHeaders().append('Content-Type', 'application/json');
    let param = new HttpParams().set("artistName", artist)
    return this.http.get('/search', {headers: headers, params: param})
    // return this.http.get(this.serverUrl+'/search', {headers: headers, params: param})
    
  }

  artistDetail(artistId: string) {
    let headers = new HttpHeaders().append('Content-Type', 'application/json');
    let param = new HttpParams().set("artistId", artistId)
    return this.http.get('/detail', {headers: headers, params: param});
    // return this.http.get(this.serverUrl+'/detail', {headers: headers, params: param});
  }

  searchArtwork(artistId: string) {
    let headers = new HttpHeaders().append('Content-Type', 'application/json');
    let param = new HttpParams().set("artistId", artistId)
    return this.http.get('/artwork', {headers: headers, params: param});
    // return this.http.get(this.serverUrl+'/artwork', {headers: headers, params: param});
  }

  getCategories(artworkId: string) {
    let headers = new HttpHeaders().append('Content-Type', 'application/json');
    let param = new HttpParams().set("artworkId", artworkId)
    return this.http.get('/categories', {headers: headers, params: param});
    // return this.http.get(this.serverUrl+'/categories', {headers: headers, params: param});
  }
}
