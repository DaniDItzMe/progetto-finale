import api from "../axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export function GenresCreateEditForm({ genre, edit }) {
  const navigate = useNavigate();

  const [errors, setErrors] = useState({});
  const server = import.meta.env.VITE_SERVER_ROOT;

  const [formData, setFormData] = useState({
    id: genre?.id || "",
    name: genre?.name || "",
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
      };
    } else {
      formGame = {
        name: formData.name,
      };
    }

    try {
      if (edit) {
        const response = await api.put(
          `/api/genres/edit/${genre.id}`,
          formGame,
        );
        console.log(response);
      } else {
        console.log("CREATING");

        const response = await api.post(`/api/genres/create`, formGame);
        console.log(response);
      }

      navigate("/genres");
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
              placeholder="Action"
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

          <div className="w-100">
            {edit && (
              <button className="btn btn-success w-100 fw-bold" type="submit">
                Edit {genre?.name}
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
