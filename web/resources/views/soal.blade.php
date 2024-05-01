@extends('layouts.layout_table')

@section('styles')
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.10.0/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
@endsection

@section('columns')    

<div class="modal fade" id="tambah_soal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Tambah Soal</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <form action="{{ route('soal.create') }}" method="post">
            <div class="modal-body">
                @csrf
                <input type="hidden" name="code" value="{{$test['test_code']}}">
                <div class="mb-3">
                    <label class="form-label">Pertanyaan</label>
                    <input type="text" class="form-control" name="pertanyaan">
                </div>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
        </div>
    </div>
</div>

<div class="card m-3">
    <div class="card-body">
        <h5 class="card-title">Daftar Soal</h5>
        <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#tambah_soal">Tambah Soal</button>
        </div>
        <div class="table-responsive">
            <table id="datatable" class="table" style="width:100%">
                <thead>
                    <tr>
                        <th>Soal</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    @if ($soals)
                    @foreach ($soals as $soal)
                        <tr>
                            <td>{{$soal['pertanyaan']}}</td>
                            {{-- <td>
                                <form action="{{ route('mata-kuliah.delete') }}" method="post">
                                <div class="btn-group">
                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#edit_{{ $mata_kuliah['mata_kuliah_code'] }}">Edit</button>
                                        <a href="{{ route('sesi-kuliah./', ['mata_kuliah_code' => $mata_kuliah['mata_kuliah_code']]) }}" class="btn btn-info">Lihat Sesi Kuliah</a>
                                        @csrf
                                        <input type="hidden" name="code" value="{{ $mata_kuliah['mata_kuliah_code'] }}">
                                        <button type="submit" class="btn btn-danger">Hapus</button>
                                    </div>
                                </form>
                            </td>                             --}}
                        </tr>

                        <!-- Edit Modal -->
                        {{-- <div class="modal fade" id="edit_{{ $mata_kuliah['mata_kuliah_code'] }}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="exampleModalLabel">Edit Mata Kuliah</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <form action="{{ route('mata-kuliah.update') }}" method="post">
                                    <div class="modal-body">
                                        @csrf
                                        @method('POST')
                                        <div class="mb-3">
                                            <label class="form-label">Nama Mata Kuliah</label>
                                            <input type="text" class="form-control" name="nama" value="{{ $mata_kuliah['mata_kuliah_name'] }}">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Kode Mata Kuliah</label>
                                            <input type="text" class="form-control" name="code" value="{{ $mata_kuliah['mata_kuliah_code'] }}">
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">Save</button>
                                    </div>
                                </form>
                                </div>
                            </div>
                        </div> --}}

                    @endforeach
                    @else
                        <tr>
                            <td colspan="4">Tidak ada data</td>
                        </tr>
                    @endif
                </tbody>
            </table>
        </div>
    </div>
</div>
@endsection

@push('script')
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.bootstrap4.min.js"></script>
    
@endpush
