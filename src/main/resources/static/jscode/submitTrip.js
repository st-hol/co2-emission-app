$(document).ready(function () {
  var form = $("#newTr");
  form.on('click', function (event) {
    var formData = {
      name: $("#name").val(),
      from: $("#from").val(),
      to: $("#to").val(),
      distanceKm: $("#distanceKm").val(),
      carId: $("#carId").val(),
      about: $("#about").val(),
      saveToHistory: $("#saveToHistory1").prop('checked'),
    };

    $.ajax({
      data: JSON.stringify(formData),
      type: 'POST',
      url: '/user/co2',
      dataType: "json",
      contentType: "application/json",
      encode: true,
    })
      .done(function (result) {
        console.log(result);
        if (result.error) {
          console.log(result.error);
        } else {
          if (result) {
            $('#errorAlert').hide();
            $('#successAlert').css({'display': 'block'});
          } else {
            $('#errorAlert')
              .text("No records found for given Sport Type")
              .show();
            $('#successAlert').hide();
          }
        }
      })
      .catch(function (reason) {
        console.log("WTF!!!");
        console.log(reason);
      });
    if (event) {
      event.preventDefault();
    }
  });
});