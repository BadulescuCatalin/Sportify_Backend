import React, { useState, useEffect } from "react";
import axios from "axios";
import Navigation from "../Navigation";
import Team from "../Team";
import { Button } from "../Button";
import Add from "./Add";
import loadingImage from "../loading.gif"; // Import your loading image

function FindTeam() {

    const [dataArray, setDataArray] = useState([]);
    const [isLoading, setIsLoading] = useState(false); // Add loading state
    const [selectedOption, setSelectedOption] = useState("none");
    const [search, setSearch] = useState("");
    const [searchResults, setSearchResults] = useState([]);
    const [searching, setSearching] = useState(false);

    const handleOptionChange = (event) => {
        setSelectedOption(event.target.value);
    }

    const handleSearch = (event) => {
        setSearch(event.target.value);
    }

    const handleSearchSubmit = (event) => {
        setSearching(true);
        event.preventDefault();
        axios({
            url: `http://localhost:8080/teams/search/${search}`,
            method: "GET",
        })
            .then((res) => {
                setSearchResults(res.data);
                setSearching(false);
            })
            .catch((err) => {
                console.log(err);
                setSearching(false);
            });
    }

    const handleJoin = async (index) => {
        let us = localStorage.getItem("username");
        await axios({
            url: "http://localhost:8080/teams/join",
            method: "POST",
            data: {
                username: us,
                teamId: searchResults[index].id,
            },
        }).catch((err) => console.log(err));
        window.location.reload();
    }

    useEffect(() => {
        let us = localStorage.getItem("username");
        setIsLoading(true);
        axios({
            url: `http://localhost:8080/teams/${us}`,
            method: "GET",
            // TODO Token?????????
        })
            .then((res) => {
                setDataArray(res.data);
                setIsLoading(false); // Set isLoading to false after the request is completed
            })
            .catch((err) => {
                console.log(err);
                setIsLoading(false); // Set isLoading to false if an error occurs
            });
    }

        , []);

    return (
        <div>
            <Navigation />
            <div className="container">
                <div className="row">
                    <div className="col-12">
                        <h1>Find a Team</h1>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        <form onSubmit={handleSearchSubmit}>
                            <div className="form-group">
                                <label htmlFor="search">Search</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="search"
                                    placeholder="Search"
                                    value={search}
                                    onChange={handleSearch}
                                />
                            </div>
                            <button type="submit" className="btn btn-primary">
                                Search
                            </button>
                        </form>
                    </div>
                </div>
                <div className="row">
                    <div className="col-12">
                        {searching ? (
                            <div className="text-center">
                                <img src={loadingImage} alt="Loading..." />
                            </div>
                        ) : (
                                <div>
                                    {searchResults.map((item, index) => (
                                        <div key={index}>
                                            <h3>{item.name}</h3>
                                            <p>{item.description}</p>
                                            <Button
                                                onClick={() => handleJoin(index)}
                                                buttonStyle="btn--primary--solid"
                                                buttonSize="btn--medium"
                                            >
                                                Join
                                            </Button>
                                        </div>
                                    ))}
                                </div>
                            )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default FindTeam;
