import { City } from "./City";
import { Visit } from "./Visit";

export interface Country {
  countryId?: number;
  countryName: string;
  countrySurface: number;
  countryPopulation: number;
  countryAbbreviation: string;
  cities?: City[];
  visits?: Visit[];
}
