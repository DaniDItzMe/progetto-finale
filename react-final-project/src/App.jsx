import { BrowserRouter, Route, Routes } from "react-router-dom";
import { DefaultLayout } from "./layouts/DefaultLayout";
import { Index } from "./pages/games/Index";
import { Show } from "./pages/games/Show";
import { NotFound } from "./pages/NotFound";
import { IndexGenres } from "./pages/genres/IndexGenres";
import { IndexConsoles } from "./pages/consoles/IndexConsoles";
import { ShowConsoles } from "./pages/consoles/ShowConsoles";
function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route element={<DefaultLayout />}>
            <Route path="/" element={<Index />}></Route>
            <Route path="/show/:id" element={<Show />}></Route>
            <Route path="/genres" element={<IndexGenres />}></Route>
            <Route path="/consoles" element={<IndexConsoles />}></Route>
            <Route path="/consoles/show/:id" element={<ShowConsoles />}></Route>

            <Route path="/*" element={<NotFound />}></Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
