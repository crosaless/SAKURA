
function verFactura(){
  const facturaId = window.location.pathname.split("/")[2];

  window.location.href = domain+"/factura/"+facturaId;
} 

