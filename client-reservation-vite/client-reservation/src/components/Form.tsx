import { useState } from "react";
import type { FormEvent } from "react";
import { Work, getWorkOptions } from "../types/reservation";
import type { ReservationCreateDTO } from "../types/reservation";
import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:25565";

function ReservationForm() {
  const [workFields, setWorkFields] = useState<number[]>([0]);
  const [formData, setFormData] = useState({
    name: "",
    surname: "",
    email: "",
    dateDay: "",
    assistanceConfirmation: false
  });

  const addWorkField = () => {
    setWorkFields([...workFields, Math.max(...workFields) + 1]);
  };

  const removeWorkField = () => {
    if (workFields.length > 1) {
      setWorkFields(workFields.slice(0, -1));
    }
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    
    const formElement = e.currentTarget;
    const formDataObj = new FormData(formElement);
    
    // Collect all selected works
    const works = formDataObj.getAll("works") as Work[];
    
    const reservation: ReservationCreateDTO = {
      name: formData.name,
      surname: formData.surname,
      email: formData.email,
      works: works,
      dateDay: formData.dateDay || undefined,
      assistanceConfirmation: formData.assistanceConfirmation
    };

    try {
      const response = await axios.post(`${API_URL}/api/reservation`, reservation);
      console.log("Reservation created:", response.data);
      alert("Reservation created successfully!");
      
      // Reset form
      formElement.reset();
      setWorkFields([0]);
      setFormData({
        name: "",
        surname: "",
        email: "",
        dateDay: "",
        assistanceConfirmation: false
      });
    } catch (error) {
      console.error("Error creating reservation:", error);
      alert("Error creating reservation. Please try again.");
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value
    }));
  };

  const workOptions = getWorkOptions();

  return (
    <div className="container mt-4">
      <h2>New Reservation</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Name *
          </label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="surname" className="form-label">
            Surname *
          </label>
          <input
            type="text"
            className="form-control"
            id="surname"
            name="surname"
            value={formData.surname}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="email" className="form-label">
            Email *
          </label>
          <input
            type="email"
            className="form-control"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="dateDay" className="form-label">
            Date
          </label>
          <input
            type="date"
            className="form-control"
            id="dateDay"
            name="dateDay"
            value={formData.dateDay}
            onChange={handleChange}
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Works *</label>
          {workFields.map((fieldId) => (
            <div key={fieldId} className="input-group mb-2">
              <label className="input-group-text" htmlFor={`workSelect-${fieldId}`}>
                Choose work type:
              </label>
              <select
                className="form-select"
                id={`workSelect-${fieldId}`}
                name="works"
                required
              >
                <option value="">Choose...</option>
                {workOptions.map(({ value, label }) => (
                  <option key={value} value={value}>
                    {label}
                  </option>
                ))}
              </select>
            </div>
          ))}
          <div className="mt-2">
            <button
              type="button"
              className="btn btn-success me-2"
              onClick={addWorkField}
            >
              + Add Work
            </button>
            <button
              type="button"
              className="btn btn-danger"
              onClick={removeWorkField}
              disabled={workFields.length === 1}
            >
              - Remove Work
            </button>
          </div>
        </div>

        <div className="mb-3 form-check">
          <input
            type="checkbox"
            className="form-check-input"
            id="assistanceConfirmation"
            name="assistanceConfirmation"
            checked={formData.assistanceConfirmation}
            onChange={handleChange}
          />
          <label className="form-check-label" htmlFor="assistanceConfirmation">
            Confirm assistance
          </label>
        </div>

        <button type="submit" className="btn btn-primary">
          Submit Reservation
        </button>
      </form>
    </div>
  );
}

export default ReservationForm;