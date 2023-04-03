import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";
import { AppHome } from "./components/AppHome";
import AppNav from "./components/AppNav";
import Countries from "./components/Countries";
import CountryDetails from "./components/CountryDetails";

function App() {
  return (
    <React.Fragment>
      <Router>
        <AppNav />
        <Routes>
          <Route path="/" element={<AppHome />} />
          <Route path="/countries" element={<Countries />} />
          <Route path="/countries/:countryId" element={<CountryDetails />} />
        </Routes>
      </Router>
    </React.Fragment>
  );
}

export default App;
