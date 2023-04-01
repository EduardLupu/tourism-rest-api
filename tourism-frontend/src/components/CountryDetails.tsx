import {
  Card,
  CardContent,
  Container,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { Component, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import BACKEND_API_URL from "../constants";
import { Country } from "../models/Country";
import ArrowBackIosNewTwoToneIcon from "@mui/icons-material/ArrowBackIosNewTwoTone";

function CountryDetails() {
  const { countryId } = useParams();
  const [loading, setLoading] = useState(false);
  const [country, setCountry] = useState<Country>();

  useEffect(() => {
    setLoading(true);
    fetch(`${BACKEND_API_URL}/countries/${countryId}`)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setCountry(data);
        setLoading(false);
      });
  }, [countryId]);

  return (
    <Container>
      <h3>Country Details:</h3>
      <Card variant="outlined">
        <CardContent>
          <div className="operations">
            <Link to={"/countries/"}>
              <ArrowBackIosNewTwoToneIcon />
            </Link>
          </div>
          <h4>
            Name: <span className="detailsInfo"> {country?.countryName}</span>
          </h4>
          <h4>
            Population:{" "}
            <span className="detailsInfo">{country?.countryPopulation}</span>
          </h4>
          <h4>
            Surface:{" "}
            <span className="detailsInfo">{country?.countrySurface}</span>
          </h4>
          <h4>
            Abbreviation:{" "}
            <span className="detailsInfo">{country?.countryAbbreviation}</span>
          </h4>
          <h4>Cities:</h4>
          <TableContainer
            sx={{ backgroundColor: "beige", borderRadius: 6 }}
            component={Paper}
          >
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>#</TableCell>
                  <TableCell>Name</TableCell>
                  <TableCell>Population</TableCell>
                  <TableCell>Surface</TableCell>
                  <TableCell>Postal Code</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {country?.cities?.map((city, index) => (
                  <TableRow key={city.cityId}>
                    <TableCell>{index + 1}</TableCell>
                    <TableCell>{city.cityName}</TableCell>
                    <TableCell>{city.cityPopulation}</TableCell>
                    <TableCell>{city.citySurface}</TableCell>
                    <TableCell>{city.cityPostalCode}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <h4> Visits:</h4>
          <TableContainer
            sx={{
              borderRadius: 6,
              backgroundColor: "beige",
            }}
            component={Paper}
          >
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>#</TableCell>
                  <TableCell>Tourist Name</TableCell>
                  <TableCell>Money Spent</TableCell>
                  <TableCell>Days Spent</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {country?.visits?.map((visit, index) => (
                  <TableRow key={visit.id}>
                    <TableCell>{index + 1}</TableCell>
                    <TableCell>{visit.touristName}</TableCell>
                    <TableCell>${visit.moneySpent}</TableCell>
                    <TableCell>{visit.daysSpent}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </CardContent>
      </Card>
    </Container>
  );
}

export default CountryDetails;
