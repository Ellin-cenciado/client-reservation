import axios from "axios"
import { useEffect, useState } from "react";
import type { Reservation } from "../types/reservation";

function Table() {

  //Reference object
  const reservationKeys = {
    id: null,
    //uuid: null,
    name: null,
    //surname: null,
    //worksAmount: null,
    works: null,
    //creationDate: null,
    dateDay: null,
    email: null,
    assistanceConfirmation: null
  }

  const headers = Object.keys(reservationKeys) as (keyof Reservation)[];

  const [reservations, setReservations] = useState<Reservation[]>([]);
  useEffect(() => {
    const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";
    const getReservations = async () => {
      try {
        const response = await axios.get(`${API_URL}/api/reservation`);
        setReservations(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    getReservations();
  }, []);

  const formatHeader = (header: keyof Reservation): string => {
    switch (header) {
      case "id":
        return "ID";
      case "name":
        return "Name";
      case "works":
        return "Works";
      case "dateDay":
        return "Date";
      case "email":
        return "Email";
      case "assistanceConfirmation":
        return "Assistance";
      default:
        return header.charAt(0).toUpperCase() + header.slice(1);
    }
  }

  const formatCell = (header: keyof Reservation, reservation: Reservation): string => {
    const value = reservation[header];

    switch (header) {
      case "name":
        const formattedName = reservation.name.charAt(0).toUpperCase() + reservation.name.slice(1).toLowerCase();
        const formattedSurname = reservation.surname.charAt(0).toUpperCase() + reservation.surname.slice(1).toLowerCase();
        return `${formattedName} ${formattedSurname}`;
      case "dateDay":
        return new Date(reservation.dateDay).toLocaleString("es-AR", {
          day: "2-digit",
          month: "2-digit",
          year: "2-digit",
          hour: "2-digit",
          minute: "2-digit"
        });
      case "assistanceConfirmation":
        console.log("Assistance value:", value);
        return value ? "✅" : "❌";

      default:
        if (Array.isArray(value)) {
          return value.join(", ");
        }
        return String(value ?? "-");
    }
  }
  const headerKeyMap = Object.fromEntries(
    headers.map(key => [formatHeader(key).toLowerCase(), key])
  );

  const [sortConfig, setSortConfig] = useState<{
    key: keyof Reservation;
    direction: "asc" | "desc";
  } | null>(null);

  const handleTableOrder = (e: React.MouseEvent<HTMLTableHeaderCellElement>) => {
    const displayName = e.currentTarget.textContent?.toLowerCase() ?? "";
    const header = headerKeyMap[displayName];

    if (!header) return;

    // Toggle direction if same header, otherwise default to asc
    const direction = sortConfig?.key === header && sortConfig.direction === "asc"
      ? "desc"
      : "asc";

    setSortConfig({ key: header, direction });

    const sortedReservations = [...reservations].sort((a, b) => {
      const valueA = a[header];
      const valueB = b[header];

      if (valueA === null || valueA === undefined) return 1;
      if (valueB === null || valueB === undefined) return -1;

      let comparison = 0;

      if (typeof valueA === "boolean" && typeof valueB === "boolean") {
        comparison = Number(valueA) - Number(valueB);
      } else if (typeof valueA === "string" && typeof valueB === "string") {
        comparison = valueA.localeCompare(valueB);
      } else if (typeof valueA === "number" && typeof valueB === "number") {
        comparison = valueA - valueB;
      } else if (valueA instanceof Date && valueB instanceof Date) {
        comparison = valueA.getTime() - valueB.getTime();
      }

      return direction === "asc" ? comparison : -comparison;
    });

    setReservations(sortedReservations);
  };

  return <>
    <table className="bg-red-800 rounded-xl overflow-hidden border-2 border-red-950 border-separate border-spacing-0">
      <thead className="bg-red-900">
        <tr>
          {headers.map((header) => (<th key={header} onClick={(e) => { handleTableOrder(e) }} className="p-2 cursor-pointer select-none">{formatHeader(header)}</th>))}
        </tr>
        
      </thead>
      <tbody className="bg-red-800 divide-y divide-red-900 hover:bg-red-900">
        {reservations.map((reservation) => (
          <tr key={reservation.id}
            className="transition duration-300 hover:bg-red-950 cursor-pointer hover:shadow-[0_0_0_2px_#93c5fd]">
            {headers.map((header) => (
              <td key={header} className="p-2">
                {formatCell(header, reservation)}
              </td>
            ))}
          </tr>
        ))
        }

      </tbody>
    </table></>;
}

export default Table;
