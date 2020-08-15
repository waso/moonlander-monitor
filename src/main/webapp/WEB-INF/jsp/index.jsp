<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/static/img/favicon.ico">

    <title>Dashboard Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/static/css/dashboard.css" rel="stylesheet">
  </head>

  <body>
    <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
      <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Dashboard</a>
    </nav>

    <div class="container-fluid">
      <div class="row">


        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
          <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
            <h3 class="h2">hash rate - 1 hour</h3>
          </div>
          <canvas class="my-4" id="last_hour" width="900" height="380"></canvas>
          <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
          <h3 class="h2">hash rate - 24 hours</h3>
          </div>
          <canvas class="my-4" id="last_24hours" width="900" height="380"></canvas>
          <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
          <h3 class="h2">hash rate - 7 days</h3>
          </div>
          <canvas class="my-4" id="last_7days" width="900" height="380"></canvas>
        </main>
      </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/static/js/jquery-3.2.1.slim.min.js"></script>
    <script>window.jQuery || document.write('<script src="/static/js/jquery-slim.min.js"><\/script>')</script>
    <script src="/static/js/popper.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>

    <!-- Icons -->
    <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
    <script>
      feather.replace()
    </script>

    <!-- Graphs -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"></script>
    <script>
      var ctx1 = document.getElementById("last_hour");
      var myChart1 = new Chart(ctx1, {
          type: 'line',
          data: {
              labels: [ <c:out value = "${labels_1h}" escapeXml="false"/> ],
              datasets: [{
                  data: [ <c:out value = "${data_1h}" /> ],
                  lineTension: 0,
                  backgroundColor: 'transparent',
                  borderColor: '#007bff',
                  borderWidth: 1,
                  pointBackgroundColor: '#007bff',
                  pointRadius: 0,
                  steppedLine: true
              }]
          },
          options: {
              scales: {
                  xAxes: [{
                  distribution: 'series',
                      ticks: {
                          source: 'labels'
                      }
                  }],
                  yAxes: [{
                      ticks: {
                          beginAtZero: false,
                          min: 0,
                          suggestedMax: 10
                      }
                  }]
              },
              legend: {
                  display: false,
              }
          }
      });
      var ctx2 = document.getElementById("last_24hours");
      var myChart2 = new Chart(ctx2, {
          type: 'line',
          data: {
              labels: [ <c:out value = "${labels_24h}" escapeXml="false"/> ],
              datasets: [{
                  data: [ <c:out value = "${data_24h}" /> ],
                  lineTension: 0,
                  backgroundColor: 'transparent',
                  borderColor: '#007bff',
                  borderWidth: 1,
                  pointBackgroundColor: '#007bff',
                  pointRadius: 0,
                  steppedLine: true
              }]
          },
          options: {
              scales: {
                  xAxes: [{
                      distribution: 'series',
                      ticks: {
                          source: 'labels'
                      }
                  }],
                  yAxes: [{
                      ticks: {
                          beginAtZero: false,
                          min: 0,
                          suggestedMax: 10
                      }
                  }]
              },
              legend: {
                  display: false,
              }
          }
      });
      var ctx3 = document.getElementById("last_7days");
      var myChart3 = new Chart(ctx3, {
          type: 'line',
          data: {
              labels: [ <c:out value = "${labels_7d}" escapeXml="false"/> ],
              datasets: [{
                  data: [ <c:out value = "${data_7d}" /> ],
                  lineTension: 0,
                  backgroundColor: 'transparent',
                  borderColor: '#007bff',
                  borderWidth: 1,
                  pointBackgroundColor: '#007bff',
                  pointRadius: 0,
                  steppedLine: true
              }]
          },
          options: {
              scales: {
                  xAxes: [{
                      distribution: 'series',
                      ticks: {
                          source: 'labels'
                      }
                  }],
                  yAxes: [{
                      ticks: {
                          beginAtZero: false,
                          min: 0,
                          suggestedMax: 10
                      }
                  }]
              },
              legend: {
                  display: false,
              }
          }
      });
    </script>
  </body>
</html>
