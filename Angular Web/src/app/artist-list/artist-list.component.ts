import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NodebackendService } from '../nodebackend.service'
import { takeUntil } from 'rxjs/operators';
import { Subject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-artist-list',
  templateUrl: './artist-list.component.html',
  styleUrls: ['./artist-list.component.css']
})
export class ArtistListComponent implements OnInit {

  constructor(private nodebackendserive: NodebackendService) { }

  searchBtnDisabled = true;
  clearBtnDisabled = true;
  searchSpinnerDisplay = 'none';
  categorySpinnerDisplay = 'none';
  detailWaitDisplay = 'none';

  resultListDisplay = 'none';
  artistDetailsDisplay = 'none';
  detailBlockDisplay = 'none';

  hasResultsDisplay = 'none';
  noResultsDisplay = 'none';
  hasArtworksDisplay = 'none';
  noArtworksDisplay = 'none';
  hasCategoriesDisplay = 'none';
  noCategoriesDisplay = 'none';

  inputName = '';
  selectedCard: Array<string>;
  artists: Array<Array<string>> = [];
  artistDetail: Array<string> = [];
  artistArtworks: Array<Array<string>> = [];
  previousArtistId: string = '';
  categories: Array<Array<string>> = [];
  categoryName: string = '';
  categoryDate: string = '';
  categoryImg: string = '';

  activeTab = 'info';
  ngOnInit() {}

  btnEnable() {
    if (this.inputName != "") {
      this.searchBtnDisabled = false;
      this.clearBtnDisabled = false;
    }
    else {
      this.searchBtnDisabled = true;
      this.searchBtnDisabled = true;
    }
  };
  setInitial() {
    this.inputName = '';
    this.resultListDisplay = 'none';
    this.artistDetailsDisplay = 'none';
  }

  artistNameSendBack() {
    // search spinner
    this.searchSpinnerDisplay = 'inline-block';
    this.activeTab = 'info';
    var res = this.nodebackendserive.searchArtists(this.inputName);
    res.subscribe(
      data => {
        this.artists = Object.values(data);
        if (this.artists.length > 0) {
          this.checkImgUrl();
          this.noResultsDisplay = 'none';
          this.searchSpinnerDisplay = 'none';
          this.detailBlockDisplay = 'none';
          this.resultListDisplay = 'block';
          this.artistDetailsDisplay = 'block';
          this.hasResultsDisplay = 'block';
          
        }
        else {
          this.hasResultsDisplay = 'none';
          this.searchSpinnerDisplay = 'none';
          this.detailBlockDisplay = 'none';
          this.resultListDisplay = 'block';
          this.artistDetailsDisplay = 'block';
          this.noResultsDisplay = 'block';
        }
      }
    );
  };

  checkImgUrl(): void {
    this.artists.forEach(function (item) {
      if (item[1] == '/assets/shared/missing_image.png') {
        item[1] = 'assets/images/artsy_logo.svg';
      }
    })
  }

  artistDetailSendBack(artist: Array<string>) {
    this.selectedCard = artist;
    this.detailWaitDisplay = 'block';
    
    this.detailBlockDisplay = 'none';
    this.activeTab = 'info';

    var artistInfoRes = this.nodebackendserive.artistDetail(artist[2]);
    artistInfoRes.subscribe(
      data => {
        this.artistDetail = Object.values(data);
        this.artistDetail[1] = (this.artistDetail[1] == null || this.artistDetail[1] == 'unknown') ? "" : this.artistDetail[1];
        this.artistDetail[2] = (this.artistDetail[2] == null || this.artistDetail[2] == 'unknown') ? "" : this.artistDetail[2];
        this.artistDetail[3] = (this.artistDetail[3] == null || this.artistDetail[3] == 'unknown') ? "" : this.artistDetail[3];
        this.artistDetail[4] = (this.artistDetail[4] == null) ? "" : this.artistDetail[4];
  
        this.detailWaitDisplay = 'none';
        this.detailBlockDisplay = 'block';
        this.artistDetailsDisplay = 'block';
      }
    );

    var artistArtworksRes = this.nodebackendserive.searchArtwork(artist[2]);
    artistArtworksRes.subscribe(
      data => {
        this.artistArtworks = Object.values(data);
        if (this.artistArtworks.length > 0) {
          this.noArtworksDisplay = 'none';
          this.hasArtworksDisplay = 'block';
        }
        else {
          this.hasArtworksDisplay = 'none';
          this.noArtworksDisplay = 'block';

        }

      }
    )
  }

  // retainColor(currentArtistId: string) {
  //   if (this.previousArtistId != '') {
  //     try {
  //       const previousCard = (document.querySelector('#artistcard-' + this.previousArtistId) as HTMLDivElement);
  //       previousCard.style.backgroundColor = '#205375';
  //     }
  //     catch (err) {
  //     }
  //   }
  //   const selectedCard = (document.querySelector('#artistcard-' + currentArtistId) as HTMLDivElement);
  //   selectedCard.style.backgroundColor = '#112B3C';
  //   this.previousArtistId = currentArtistId;
  // }


  searchInfo(activeTab: string) {
    this.activeTab = activeTab;
  }

  searchArtworks(activeTab: string) {
    this.activeTab = activeTab;
  }

  artworkCateSendBack(artworkId: string) {
   
    this.categorySpinnerDisplay = 'block';
    this.noCategoriesDisplay = 'none';
    this.hasCategoriesDisplay = 'none';
    
    var artworkCategoriesRes = this.nodebackendserive.getCategories(artworkId);
    artworkCategoriesRes.subscribe(
      data => {
        this.categories = Object.values(data);
        // console.log(this.categories);
     
        this.categorySpinnerDisplay = 'none';
        if (this.categories.length > 0) {
          this.noCategoriesDisplay = 'none';
          this.hasCategoriesDisplay = 'block';
        }
        else {
          this.hasCategoriesDisplay = 'none';
          this.noCategoriesDisplay = 'block';
        }
        this.categorySpinnerDisplay = 'none';
      }
    )
  }
  currentCategory(artworkId: string, name: string, date: string, img: string) {
    this.categoryName = name;
    this.categoryDate = date;
    this.categoryImg = img;
  }
}
