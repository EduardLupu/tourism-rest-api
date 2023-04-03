import { Visit } from "./Visit";

export interface Tourist {
  id: number;
  touristName: string;
  touristDateOfBirth: Date;
  touristGender: string;
  touristAge: number;
  visits: Visit[];
}
