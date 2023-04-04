import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";
import { AppHome } from "./components/AppHome";
import AppNav from "./components/AppNav";
import Countries from "./components/Countries";
import CountryDetails from "./components/CountryDetails";
import { Tourists } from "./components/Tourists";
import { Visits } from "./components/Visits";

function App() {
  return (
    <React.Fragment>
      <Router>
        <AppNav />
        <Routes>
          <Route path="/" element={<AppHome />} />
          <Route path="/countries" element={<Countries />} />
          <Route path="/countries/:countryId" element={<CountryDetails />} />
          <Route path="/tourists" element={<Tourists />} />
          <Route path="/visits" element={<Visits />} />
        </Routes>
      </Router>
    </React.Fragment>
  );
}

export default App;
