const char MAIN_page[] PROGMEM = R"=====(
<!DOCTYPE html>
<html>
<body onload="getInitialValues()">

<div>
  Temperature: <span id="temperatureValue">0</span><br>
  Humidity: <span id="humidityValue">NA</span>
</div>

<script>

function getInitialValues() {
  getTemperature();
  getHumidity();
}

function getTemperature() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("temperatureValue").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "readTemperature", true);
  xhttp.send();
}
function getHumidity() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("humidityValue").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "readHumidity", true);
  xhttp.send();
}
</script>
</body>
</html>
)=====";
