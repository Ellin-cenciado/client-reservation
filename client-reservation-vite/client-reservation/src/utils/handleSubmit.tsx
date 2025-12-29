import { Work } from "../types/reservation";

function handleSubmit(event: React.FormEvent<HTMLFormElement>) { {
    event.preventDefault();
    const target = event.target as typeof event.target & {
        name: { value: string };
        surname: { value: string };
        email: { value: string };
        confirmAssistance: { checked: boolean };
        works: { value: Work[] };
    };
    const name = target.name.value;
    const surname = target.surname.value;
    const email = target.email.value;
    const confirmAssistance = target.confirmAssistance.checked;
    const works = target.works.value;

    // Add validation logic here
    if (!name || !surname || !email || !works) {
        alert("Please fill in all required fields");
        return;
    }
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        alert("Invalid email format");
        return;
    }

    // Process the form data as needed
    console.log({
        name,
        surname,
        email,
        confirmAssistance,
        works,
    });
} }

export default handleSubmit;