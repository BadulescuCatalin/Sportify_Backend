import React, { useState, useEffect } from "react";
import axios from "axios";
import Navigation from "../Navigation";
import Court from "../Court";
import loadingImage from "../loading.gif"; // Import your loading image

function Book() {
  const [dataArray, setDataArray] = useState([]);
  const [selectedOption, setSelectedOption] = useState("none");
  const [isLoading, setIsLoading] = useState(false); // Add loading state

  const handleReserve = (index) => {
    const newUrl = `/Main/Book/Reservation?index=${dataArray[index].id}`;
    window.history.pushState({}, "", newUrl);
    window.location.reload();
    return;
  };

  const handleOptionChange = (event) => {
    setSelectedOption(event.target.value);
  };

  useEffect(() => {
    let us = localStorage.getItem("username");
    setIsLoading(true);
    axios({
      url: `http://localhost:8080/sorted/${selectedOption}`,
      method: "GET",
      // TODO Token?????????
    })
      .then((res) => {
        setDataArray(res.data);
        setIsLoading(false);
      })
      .catch((err) => {
        console.log(err);
        setIsLoading(false);
      });
  }, [selectedOption]); // Dependency array with selectedOption as a dependency

  useEffect(() => {
    setIsLoading(true);
    axios({
      url: "http://localhost:8080/fields",
      method: "GET",
      // TODO Token?????????
    }).then((res) => {
      setDataArray(res.data);
    });

    setIsLoading(false);
  }, []);
  return (
    <div>
      <div>
        <Navigation></Navigation>
      </div>
      <div
        style={{
          backgroundColor: "gray",
          position: "fixed",
          marginTop: "80px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          width: "100%",
        }}
      >
        <select value={selectedOption} onChange={handleOptionChange}>
          <option value="none">none</option>
          <option value="price asc">price asc</option>
          <option value="price desc">price desc</option>
          <option value="basketball">basketball</option>
          <option value="football">football</option>
          <option value="tennis">tennis</option>
        </select>
      </div>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <div
          className="courts-container"
          style={{
            marginTop: "10%",
            width: "50%",
            position: "relative",
          }}
        >
          {dataArray.map((item, index) => (
            <>
              <Court obiect={item} key={item.key} />
              <button onClick={() => handleReserve(index)}> Rezerva </button>
            </>
          ))}
        </div>
      </div>
      {isLoading && (
        <div className="loading-container">
          <div className="loading-overlay" />
          <div className="loading-box">
            <img src={loadingImage} alt="Loading" className="loading-image" />
          </div>
        </div>
      )}
    </div>
  );
}
export default Book;
