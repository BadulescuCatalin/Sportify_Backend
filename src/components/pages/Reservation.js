import React, { useState, useEffect } from "react";
import axios from "axios";
import Navigation from "../Navigation";
import Court from "../Court";
import { Button } from "../Button";
import { useLocation } from "react-router-dom";
import "./Reservation.css";

function Reservation() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const index = queryParams.get("index");
  const [response, setResponse] = useState(null);
  const [done, setDone] = useState(false);

  // cerere cu datele terenului dupa id-ul din URL
  const fetchData = async () => {
    try {
      setResponse(await axios.get(`http://localhost:8080/id/${index}`));
      setDone(true);
    } catch (error) {}
  };

  useEffect(() => {
    fetchData();
  });

  return (
    <div>
      <div>
        <Navigation></Navigation>
      </div>
      <div>
        {/* cardul pentru acel teren cum fac iar obiectul? +tabel pentru
        intervale orare */}
        {response ? <Court obiect={response.data}></Court> : <p>Loading...</p>}
        <table>
          <tr>
            <th> Ziua </th>
            <th> Ora </th>
            <th> Rezerva!</th>
          </tr>
        </table>
      </div>
    </div>
  );
}

export default Reservation;
