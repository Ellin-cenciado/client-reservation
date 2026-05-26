// src/types/reservation.ts

/**
 * Work type enum - matches Java Reservation.Work
 */
export const Work = {
  PIERCING: "PIERCING",
  TATTOO: "TATTOO",
  REVISION: "REVISION",
  CHANGE: "CHANGE",
  OTHER: "OTHER"
} as const;

export type Work = typeof Work[keyof typeof Work];

/**
 * Display names for work types
 */
export const WorkDisplayNames: Record<Work, string> = {
  [Work.PIERCING]: "Piercing",
  [Work.TATTOO]: "Tattoo",
  [Work.REVISION]: "Revision",
  [Work.CHANGE]: "Change",
  [Work.OTHER]: "Other"
};

/**
 * Reservation interface - matches Java Reservation entity
 */
export interface Reservation {
  id?: number;
  uuid?: string;
  name: string;
  surname: string;
  worksAmount?: number;
  works: Work[];
  creationDate?: string; // ISO date string from backend
  dateDay: string; // ISO date string
  email: string;
  assistanceConfirmation: boolean;
}

/**
 * DTO for creating a new reservation (fields that user provides)
 */
export interface ReservationCreateDTO {
  name: string;
  surname: string;
  works: Work[];
  dateDay?: string;
  email: string;
  assistanceConfirmation: boolean;
}

/**
 * Helper function to get work display name
 */
export const getWorkDisplayName = (work: Work): string => {
  return WorkDisplayNames[work];
};

/**
 * Helper function to get all work options for select dropdown
 */
export const getWorkOptions = () => {
  return Object.values(Work).map(work => ({
    value: work,
    label: WorkDisplayNames[work]
  }));
};