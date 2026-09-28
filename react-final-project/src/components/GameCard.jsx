export function GameCard({ game }) {
  const server = import.meta.env.VITE_SERVER_ROOT;

  return (
    <>
      <div className="card">
        <h5 className="card-header py-3">{game.name}</h5>
        <div className="card-body">
          <div className="d-flex gap-4">
            <div>
              <img
                className="img-fluid rounded-2"
                style={{ width: "auto", height: "500px", objectFit: "fill" }}
                src={`${server}api/games/image/` + game.id}
                alt={game.name}
              />
            </div>
            <div className="d-flex flex-column">
              <div className="mb-3">
                <h4>Description</h4>
                <p className="card-text">{game.description}</p>
              </div>

              <div className="mb-3">
                <h6 className="d-inline">Genres:</h6>

                {game.genres.length > 0 && (
                  <ul>
                    {game.genres.map((el) => (
                      <li key={el.id} className="card-text">
                        {el.name}
                      </li>
                    ))}
                  </ul>
                )}

                {game.genres.length == 0 && (
                  <p
                    th:unless="*{genres.size() > 0}"
                    className="card-text d-inline"
                  >
                    N/A
                  </p>
                )}
              </div>

              <div className="mb-3">
                <h6 className="d-inline">Developer: </h6>
                <p className="card-text d-inline">{game.developer}</p>
              </div>

              <div className="mb-3">
                <h6 className="d-inline">Publisher: </h6>
                <p className="card-text d-inline">{game.publisher}</p>
              </div>

              <div className="mb-3">
                <h6 className="d-inline">Available on:</h6>
                {game.availableConsoles.length > 0 && (
                  <ul>
                    {game.availableConsoles.map((el) => (
                      <li key={el.id} className="card-text">
                        {el.name}
                      </li>
                    ))}
                  </ul>
                )}

                {game.availableConsoles.length == 0 && (
                  <p className="card-text d-inline">N/A</p>
                )}
              </div>

              <div className="d-flex justify-content-end align-items-end flex-grow-1">
                <div className="btn btn-success rounded-pill px-4 py-3">
                  <h4 className="fw-bold d-inline">Price: </h4>
                  <h5 className="d-inline">{game.price}€</h5>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
