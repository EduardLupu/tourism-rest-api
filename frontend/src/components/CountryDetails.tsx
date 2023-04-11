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
  TablePagination,
  TableRow,
} from "@mui/material";
import { Component, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import BACKEND_API_URL from "../constants";
import { Country } from "../models/Country";
import ArrowBackIosNewTwoToneIcon from "@mui/icons-material/ArrowBackIosNewTwoTone";
import React from "react";

function CountryDetails() {
  const { countryId } = useParams();
  const [loading, setLoading] = useState(false);
  const [country, setCountry] = useState<Country>();
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage2, setRowsPerPage2] = React.useState(5);
  const [page2, setPage2] = React.useState(0);

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const handleChangePage2 = (event: unknown, newPage: number) => {
    setPage2(newPage);
  };

  const handleChangeRowsPerPage2 = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setRowsPerPage2(+event.target.value);
    setPage2(0);
  };

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
            <TablePagination
              rowsPerPageOptions={[5, 10, 25]}
              component="div"
              count={country?.cities?.length || 0}
              rowsPerPage={rowsPerPage}
              page={page}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
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
            <TablePagination
              rowsPerPageOptions={[5, 10, 25]}
              component="div"
              count={country?.visits?.length || 0}
              rowsPerPage={rowsPerPage2}
              page={page2}
              onPageChange={handleChangePage2}
              onRowsPerPageChange={handleChangeRowsPerPage2}
            />
          </TableContainer>
        </CardContent>
      </Card>
    </Container>
  );
}

export default CountryDetails;
