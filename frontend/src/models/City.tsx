import { Country } from "./Country";

export interface City {
  cityId: number;
  cityName: string;
  cityPopulation: number;
  citySurface: number;
  cityPostalCode: number;
  country?: Country;
}
