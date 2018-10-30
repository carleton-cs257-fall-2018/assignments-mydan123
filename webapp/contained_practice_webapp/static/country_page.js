
initialize();

function initialize() {
	changeTitle();
}

function getBaseURL() {
    var baseURL = window.location.protocol + '//' + window.location.hostname + ':' + api_port;
    return baseURL;
}

function changeTitle() {
    var titles = document.getElementsByTagName('title');
    if (titles.length > 0) {
        titles[0].innerHTML = country_name;
    }
}

