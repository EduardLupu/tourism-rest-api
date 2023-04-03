import { Country } from "./Country";
import { Tourist } from "./Tourist";

export interface Visit {
  id: number;
  touristId: number;
  countryId: number;
  moneySpent: number;
  daysSpent: number;
  touristName: string;
  countryName: string;
}
