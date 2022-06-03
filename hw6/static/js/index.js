var prevCardId = "";
function showLoadingImg1() {
    var img = document.getElementById("loading-img-1");
    img.style.display = "flex";
}
function hideLoadingImg1() {
    var img = document.getElementById("loading-img-1");
    img.style.display = "none";
}
function showLoadingImg2() {
    var img = document.getElementById("loading-img-2");
    img.style.display = "flex";
}
function hideLoadingImg2() {
    var img = document.getElementById("loading-img-2");
    img.style.display = "none";
}
function showSearchDiv() {
    var div = document.querySelector(".search-result-form");
    div.style.display = "flex";
}
function hideSearchDiv() {
    var div = document.querySelector(".search-result-form");
    div.style.display = "none";
}
function showDetailDiv() {
    var div = document.querySelector(".detail-container");
    div.style.display = "block";
}
function hideDetailDiv() {
    var div = document.querySelector(".detail-container");
    div.style.display = "none";
}
// search artitst
function artistNameSendBack(event) {
    
    const myHeaders = new Headers();
    var artistName = document.getElementById("search").value;
    if (artistName) {
        // hideSearchDiv();
        hideDetailDiv();

        showLoadingImg1();
        
        myHeaders.append("artistName", artistName);
        const myrequest = new Request("/search/"+artistName);
        fetch(myrequest, {
            method: "GET",
            headers: myHeaders,
            mode: 'cors',
            cache: 'default',
        }).then(function (response) {
            // window.setTimeout(showLoadingImg, 5000);
            // showLoadingImg1();
            // setTimeout(function() {
            //     console.log("wait!");
            //   }, 5000);
            return response;
        })
            .then(function (response) {
                data = response.json();
                return data;
            })
            .then(function (data) {
                // not display loading img
                //window.setTimeout(hideLoadingImg, 5000);
                // hideLoadingImg1();
                return data;
            })
            .then(function (data) {
                var showhtml = ""
                // var detailcontainer = document.querySelector(".detail-container");
                // detailcontainer.style.display = "none";
                if (data.length > 0) {
                    
                    for (var i = 0; i < data.length; i++) {
                        var name = data[i][0];
                        var imgSrc = data[i][1];
                        var id = data[i][2];
                        if (imgSrc == '/assets/shared/missing_image.png') {
                            imgSrc = "static/images/artsy_logo.svg";
                        }
                        var queryDetail = 'artistDetail(event, \"' + id + '\")';
                        //var retainBackgroudColor = 'retainColor(event, \"' + id + '\",\"'+ prevCardId +'\")';
                        
                    
                        var retainBackgroudColor = 'retainColor(event, \"' + id + '\")';
                        showhtml += "<div id='card-"+ id + "' class='card' onclick='" + queryDetail + ";" + retainBackgroudColor + "'>" +
                            "<img class='art-img' src=" + imgSrc + " width='150' height='150'>" +
                            "<p class='artistname'>" + name + "</p></div>";
                        
                        
                    }
                } else {
                    // var detailcontainer = document.querySelector(".detail-container");
                    // detailcontainer.style.display = "none";
                    showhtml = "<div class='no-result'><p class='noresult-box' style='text-align:center;'>No results found.</p></div>";
                }
                hideLoadingImg1();
                showSearchDiv();
                document.querySelector(".search-result-form").innerHTML = showhtml;
                // var detailcontainer = document.querySelector(".detail-container");
                // detailcontainer.style.display = "none";
            })
            .catch((error) => {
                console.error('Error:', error);
            });

        event.preventDefault();
    }

}


function artistDetail(event, id) {
    const myHeaders = new Headers();
    hideDetailDiv();
    if (id) {
        
        showLoadingImg2();
        
        myHeaders.append("id", id);
        const myrequest = new Request("/detail/"+id);
        fetch(myrequest, {
            method: "GET",
            headers: myHeaders,
            mode: 'cors',
            cache: 'default',
        }).then(function (response) {
            // showLoadingImg2();
            return response;
        })
            .then(function (response) {
                data = response.json();
                return data;
            })
            .then(function (data) {
                // hideLoadingImg2();
                return data;
            }).then(function (data) {
                if(data.length>0) {
                    var detailcontainer = document.querySelector(".detail-container");
                    detailcontainer.style.display = "block";
                    var name = data[0]==null ? "" : data[0];
                    var birthday =  (data[1]==null || data[1]=='unknown') ? "" : data[1];
                    var deathday = (data[2]==null || data[2]=='unknown') ? "" : data[2];
                    var nationality = data[3]==null ? "" : data[3];
                    var biography = data[4]==null ? "" : data[4];

                    var currenthtml = "<p>" + name + " ( " + birthday + " - " + deathday + " )</p>" + 
                    "<p style='font-size: 17px;'>" + nationality + "</p>" +
                    "<p class='paragraph'>" + biography + "</p>";
                    hideLoadingImg2();
                    showDetailDiv();
                    document.querySelector(".detail-container").innerHTML = currenthtml;
                }

            }).catch((error) => {
                console.error('Error:', error);
            });

        event.preventDefault();
    }
}

function retainColor(event, id) {
    if(prevCardId != '') {
        try{
            var prev_card = document.querySelector('#card-'+prevCardId);
            prev_card.style.backgroundColor = '#205375';
        }
        catch(err) {
        }
    } 
    var card = document.querySelector('#card-'+id);
    card.style.backgroundColor = '#112B3C';
    prevCardId = id;
}