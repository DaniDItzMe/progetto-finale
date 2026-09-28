import api from "../axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export function ConsolesCreateEditForm({ gameConsole, edit }) {
  const navigate = useNavigate();

  const [errors, setErrors] = useState({});

  const server = import.meta.env.VITE_SERVER_ROOT;

  const [formData, setFormData] = useState({
    id: gameConsole?.id || "",
    name: gameConsole?.name || "",
    description: gameConsole?.description || "",
    price: gameConsole?.price || "",
  });

  const handleFormData = (e) => {
    const { name, value } = e.target;

    setFormData((c) => {
      return {
        ...c,
        [name]: value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let formGame;
    if (edit) {
      formGame = {
        id: formData.id,
        name: formData.name,
        description: formData.description,
        price: formData.price,
      };
    } else {
      formGame = {
        name: formData.name,
        description: formData.description,
        price: formData.price,
      };
    }

    try {
      if (edit) {
        const response = await api.put(
          `/api/consoles/edit/${gameConsole.id}`,
          formGame,
        );
        console.log(response);
      } else {
        console.log("CREATING");

        const response = await api.post(`/api/consoles/create`, formGame);
        console.log(response);
      }

      navigate("/consoles");
    } catch (err) {
      console.log(err.response);
      setErrors(err.response.data);
    }
  };

  return (
    <>
      <div className="border border-2 w-75 rounded-4 p-4 mb-5 border-dark">
        <form onSubmit={handleSubmit}>
          <input type="hidden" name="id" value={formData.id} />

          <div className="mb-3">
            <label htmlFor="name" className="form-label fw-bold">
              Name
            </label>
            <input
              type="text"
              className={`form-control ${errors?.name ? "is-invalid" : ""}`}
              placeholder="Playstation 5"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleFormData}
            />

            {errors?.name && (
              <div className="text-danger">
                <ul>
                  <li className="fw-bold">{errors.name}</li>
                </ul>
              </div>
            )}
          </div>

          <div className="mb-3">
            <label htmlFor="description" className="form-label fw-bold">
              Description
            </label>
            <input
              type="text"
              className={`form-control ${errors?.description ? "is-invalid" : ""}`}
              placeholder="Sony home console featuring high performance, fast loading times and support for modern games with advanced graphics."
              id="description"
              name="description"
              value={formData.description}
              onChange={handleFormData}
            />

            {errors?.description && (
              <div className="text-danger">
                <ul>
                  <li className="fw-bold">{errors.description}</li>
                </ul>
              </div>
            )}
          </div>

          <div className="mb-3">
            <label htmlFor="price" className="form-label fw-bold">
              Price
            </label>
            <input
              type="text"
              className={`form-control ${errors?.price ? "is-invalid" : ""}`}
              placeholder="549.99"
              id="price"
              name="price"
              value={formData.price}
              onChange={handleFormData}
            />

            {errors?.price && (
              <div className="text-danger">
                <ul>
                  <li className="fw-bold">{errors.price}</li>
                </ul>
              </div>
            )}
          </div>

          <div className="w-100">
            {edit && (
              <button className="btn btn-success w-100 fw-bold" type="submit">
                Edit {gameConsole?.name}
              </button>
            )}
            {!edit && (
              <button className="btn btn-success w-100 fw-bold" type="submit">
                Add new genre
              </button>
            )}
          </div>
        </form>
      </div>
    </>
  );
}
