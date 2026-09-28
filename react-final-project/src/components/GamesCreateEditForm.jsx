import api from "../axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

export function GamesCreateEditForm({ game, genres, consoles, edit }) {
  const navigate = useNavigate();

  const [errors, setErrors] = useState({});
  const server = import.meta.env.VITE_SERVER_ROOT;

  const [formData, setFormData] = useState({
    id: game?.id || "",
    name: game?.name || "",
    description: game?.description || "",
    genres: game?.genres.map((genre) => genre.id),
    availableConsoles: game?.availableConsoles.map((console) => console.id),
    developer: game?.developer || "",
    publisher: game?.publisher || "",
    price: game?.price || "",
    coverImage: null,
  });

  const handleFormData = (e) => {
    const { name, value, type, files, checked } = e.target;

    if (type == "file") {
      setFormData((c) => {
        return {
          ...c,
          [name]: files[0] ?? null,
        };
      });

      return;
    }

    if (type == "checkbox") {
      setFormData((c) => {
        let currentValues = c[name] || [];
        return {
          ...c,
          [name]: checked
            ? [...currentValues, Number(value)]
            : currentValues.filter((currentId) => currentId !== Number(value)),
        };
      });

      return;
    }

    setFormData((c) => {
      return {
        ...c,
        [name]: value,
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = new FormData();

    let formGame;
    if (edit) {
      formGame = {
        id: formData.id,
        name: formData.name,
        description: formData.description,
        genres: formData.genres.map((genreId) => ({
          id: genreId,
        })),
        availableConsoles: formData.availableConsoles.map((console) => ({
          id: console,
        })),
        developer: formData.developer,
        publisher: formData.publisher,
        price: formData.price,
      };
    } else {
      formGame = {
        name: formData.name,
        description: formData.description,
        genres: formData.genres.map((genreId) => ({
          id: genreId,
        })),
        availableConsoles: formData.availableConsoles.map((console) => ({
          id: console,
        })),
        developer: formData.developer,
        publisher: formData.publisher,
        price: formData.price,
      };
    }

    data.append(
      "game",
      new Blob([JSON.stringify(formGame)], {
        type: "application/json",
      }),
    );
    data.append("coverImage", formData.coverImage);

    try {
      if (edit) {
        const response = await api.put(`/api/games/edit/${game.id}`, data);
        console.log(response);
      } else {
        console.log("CREATING");

        const response = await api.post(`/api/games/create`, data);
        console.log(response);
      }

      navigate("/");
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
              placeholder="The Last of Us Part I"
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
            <textarea
              className={`form-control ${errors?.description ? "is-invalid" : ""}`}
              placeholder="Experience Joel and Ellie's adventure in search of a cure for the Cordyceps"
              id="description"
              name="description"
              value={formData.description}
              onChange={handleFormData}
            ></textarea>

            {errors?.description && (
              <div className="text-danger">
                <ul>
                  <li className="fw-bold">{errors.description}</li>
                </ul>
              </div>
            )}
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold">Genre</label>

            {genres?.map((genre, index) => (
              <div key={index}>
                <input
                  type="checkbox"
                  className="form-check-input"
                  id={`genre-${genre.id}`}
                  value={genre.id}
                  name="genres"
                  checked={formData.genres.includes(genre.id)}
                  onChange={handleFormData}
                />

                <label
                  className="form-check-label"
                  htmlFor={`genre-${genre.id}`}
                >
                  {genre.name}
                </label>
              </div>
            ))}
            {errors?.genres && (
              <div className="text-danger">
                <ul>
                  <li className="fw-bold">{errors.genres}</li>
                </ul>
              </div>
            )}
          </div>

          <div className="mb-3">
            <label className="form-label fw-bold">Available on</label>

            <div>
              {consoles?.map((console, index) => (
                <div key={index} className="block">
                  <input
                    type="checkbox"
                    className="form-check-input"
                    id={console.name}
                    name="availableConsoles"
                    value={console.id}
                    checked={formData.availableConsoles.includes(console.id)}
                    onChange={handleFormData}
                  />
                  <label htmlFor={console.name}>{console.name}</label>
                </div>
              ))}
            </div>

            {errors?.availableConsoles && (
              <div className="text-danger">
                <ul>
                  <li className="fw-bold">{errors.availableConsoles}</li>
                </ul>
              </div>
            )}
          </div>

          <div className="mb-3">
            <label htmlFor="developer" className="form-label fw-bold">
              Developer
            </label>
            <input
              type="text"
              className={`form-control ${errors?.developer ? "is-invalid" : ""}`}
              placeholder="Naughty dog"
              id="developer"
              name="developer"
              value={formData.developer}
              onChange={handleFormData}
            />

            {errors?.developer && (
              <div className="text-danger">
                <ul>
                  <li className="fw-bold">{errors.developer}</li>
                </ul>
              </div>
            )}
          </div>

          <div className="mb-3">
            <label htmlFor="publisher" className="form-label fw-bold">
              Publisher
            </label>
            <input
              type="text"
              className={`form-control ${errors?.publisher ? "is-invalid" : ""}`}
              placeholder="Naughty dog"
              id="publisher"
              name="publisher"
              value={formData.publisher}
              onChange={handleFormData}
            />

            {errors?.publisher && (
              <div className="text-danger">
                <ul>
                  <li className="fw-bold">{errors.publisher}</li>
                </ul>
              </div>
            )}
          </div>

          <div className="mb-3">
            <label htmlFor="coverImage" className="form-label fw-bold">
              Cover image
            </label>
            <input
              type="file"
              className="form-control"
              id="coverImage"
              name="coverImage"
              accept="image/jpeg"
              onChange={handleFormData}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="price" className="form-label fw-bold">
              Price
            </label>
            <input
              type="number"
              step="0.01"
              className={`form-control ${errors?.price ? "is-invalid" : ""}`}
              placeholder="69.99"
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
                Edit {game?.name}
              </button>
            )}
            {!edit && (
              <button
                th:unless="${edit}"
                className="btn btn-success w-100 fw-bold"
                type="submit"
              >
                Add new game
              </button>
            )}
          </div>
        </form>
      </div>
    </>
  );
}
